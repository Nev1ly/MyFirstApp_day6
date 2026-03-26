package ru.krasnodarova.myfirstapp.repository
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.krasnodarova.myfirstapp.dto.Post
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PostRepositoryFileImpl(
    private val context: Context
) : PostRepository {

    private val gson = Gson()
    private val filename = "posts.json"

    // Тип для десериализации списка постов
    private val type = object : TypeToken<List<Post>>() {}.type

    // Счетчик для генерации ID
    private var nextId = 1L

    // Текущий пользователь
    private val currentUserId = 1L
    private val currentUserName = "Я"

    // Данные в памяти
    private var posts = emptyList<Post>()
    private val _data = MutableLiveData(posts)
    init {
        // При создании репозитория пытаемся загрузить данные из файла
        loadData()
    }
    override fun getAll(): LiveData<List<Post>> = _data
    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    likedByMe = !post.likedByMe,
                    likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
                )
            } else {
                post
            }
        }
        _data.value = posts
        saveData()
    }
    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(shares = post.shares + 1)
            } else {
                post
            }
        }
        _data.value = posts
        saveData()
    }
    override fun increaseViews(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(views = post.views + 1)
            } else {
                post
            }
        }
        _data.value = posts
        saveData()
    }
    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            // Создание нового поста
            val newPost = post.copy(
                id = generateNextId(),
                author = currentUserName,
                authorId = currentUserId,
                published = formatDate(Date()),
                likedByMe = false,
                likes = 0,
                shares = 0,
                views = 0
            )
            listOf(newPost) + posts
        } else {
            // Обновление существующего поста
            posts.map { existingPost ->
                if (existingPost.id == post.id) {
                    existingPost.copy(content = post.content)
                } else {
                    existingPost
                }
            }
        }
        _data.value = posts
        saveData()
    }
    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        _data.value = posts
        saveData()
    }
    private fun loadData() {
        val file = getPostsFile()
        if (!file.exists()) {
            // Если файла нет, создаем начальные данные
            createInitialData()
            saveData()
            return
        }

        try {
            context.openFileInput(filename).bufferedReader().use { reader ->
                val loadedPosts: List<Post> = gson.fromJson(reader, type)
                if (loadedPosts.isNotEmpty()) {
                    posts = loadedPosts
                    // Вычисляем следующий ID на основе максимального существующего
                    nextId = (posts.maxOfOrNull { it.id } ?: 0) + 1
                    _data.value = posts
                } else {
                    createInitialData()
                    saveData()
                }
            }
        } catch (e: Exception) {
            // В случае ошибки создаем начальные данные
            e.printStackTrace()
            createInitialData()
            saveData()
        }
    }
    private fun saveData() {
        try {
            context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use { writer ->
                gson.toJson(posts, writer)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun getPostsFile(): File = context.filesDir.resolve(filename)
    private fun createInitialData() {
        posts = listOf(
            Post(
                id = 1,
                author = "Книжный клуб «Переплёт»",
                content = "Друзья, на этой неделе мы погружаемся в мир великого романа Михаила Булгакова — «Мастер и Маргарита»! Делитесь своими любимыми цитатами, впечатлениями и теориями. Какая сцена вас тронула больше всего? Присоединяйтесь к обсуждению в комментариях!",
                published = "12 марта в 08:06",
                likedByMe = false,
                likes = 1420,
                shares = 310,
                views = 9800,
                video = null
            ),
            Post(
                id = 2,
                author = "Healthy Buddy — персональный помощник здоровья!",
                content = "Вышел новый трек-лист для тренировок! Заходи на сайт и слушай!",
                published = "12 марта в 10:15",
                likedByMe = true,
                likes = 3042,
                shares = 89,
                views = 23000,
                video = "https://www.youtube.com/watch?v=ojVA7Rs4gtI"
            ),
            Post(
                id = 3,
                author = "ТехноМир",
                content = "Представляем вашему вниманию топ-5 гаджетов, которые, по мнению экспертов, изменят нашу повседневную жизнь в 2026 году. В нашем обзоре — умные очки с дополненной реальностью, компактные зарядные станции и многое другое. Подробности и обзоры — по ссылке в профиле!\t",
                published = "13 марта в 09:42",
                likedByMe = false,
                likes = 2110,
                shares = 470,
                views = 15300
            ),
            Post(
                id = 4,
                author = "Киномания",
                content = "Выходные — идеальное время для кино! Мы собрали для вас подборку самых интересных новинок марта: от драм до комедий и фантастики. Какой фильм выберете вы? Пишите в комментариях свои варианты!",
                published = "14 марта в 08:00",
                likedByMe = false,
                likes = 2340,
                shares = 520,
                views = 18900
            ),
            Post(
                id = 5,
                author = "Психология и я",
                content = "Стресс — частый спутник современной жизни. Мы собрали 7 простых и эффективных способов, которые помогут вам справиться с напряжением и вернуть гармонию. Попробуйте уже сегодня!",
                published = "14 марта в 15:40",
                likedByMe = false,
                likes = 1890,
                shares = 390,
                views = 11200
            ),
            Post(
                id = 6,
                author = "ГастроТур",
                content = "Гастрономическая Москва не перестаёт удивлять! В нашем новом обзоре — лучшие рестораны марта: от уютных семейных заведений до изысканных авторских кухонь. Куда сходить — решать вам!",
                published = "15 марта в 05:10",
                likedByMe = false,
                likes = 1560,
                shares = 340,
                views = 10100
            ),
            Post(
                id = 7,
                author = "IT-новости",
                content = "\tИскусственный интеллект активно внедряется в образование. Как это меняет процесс обучения и какие перспективы открывает для студентов и преподавателей? Читайте наш свежий материал!",
                published = "15 марта в 17:30",
                likedByMe = false,
                likes = 2030,
                shares = 450,
                views = 14700
            ),
            Post(
                id = 8,
                author = "Мода и стиль",
                content = "Весна-лето 2026 года приносит свежие идеи и яркие тренды! В нашем обзоре — главные цвета, фасоны и аксессуары сезона. Будьте в курсе модных новинок и вдохновляйтесь!",
                published = "15 марта в 20:00",
                likedByMe = true,
                likes = 2450,
                shares = 580,
                views = 20300
            ),
            Post(
                id = 9,
                author = "Путешествия по России",
                content = "Лето на Алтае — это незабываемые впечатления! Мы составили топ-5 мест, которые стоит посетить: от горных озёр до древних курганов. Планируйте свой маршрут вместе с нами!",
                published = "16 марта в 07:00",
                likedByMe = true,
                likes = 1340,
                shares = 290,
                views = 9200
            ),
            Post(
                id = 10,
                author = "DIY идеи",
                content = "Создайте стильный декор для дома своими руками! В нашем новом мастер-классе — пошаговая инструкция по изготовлению оригинальных украшений из подручных материалов. Вдохновляйтесь и творите!",
                published = "16 марта в 07:30",
                likedByMe = false,
                likes = 1920,
                shares = 430,
                views = 12900
            ),
            Post(
                id = 11,
                author = "Наука и открытия",
                content = "Учёные выяснили: качественный сон напрямую влияет на память и способность к обучению. Узнайте, как улучшить свой сон и повысить продуктивность, в нашей новой статье!",
                published = "16 марта в 12:30",
                likedByMe = false,
                likes = 1650,
                shares = 370,
                views = 11800
            )
        )
        _data.value = posts
    }
    private fun generateNextId(): Long = nextId++
    private fun formatDate(date: Date): String {
        val format = SimpleDateFormat("d MMM в HH:mm", Locale("ru"))
        return format.format(date)
    }
}
