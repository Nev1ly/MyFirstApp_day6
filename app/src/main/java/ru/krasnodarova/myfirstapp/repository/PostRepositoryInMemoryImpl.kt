package ru.krasnodarova.myfirstapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.krasnodarova.myfirstapp.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    // Исходные данные
    private var posts = listOf(
        Post(
        id = 1,
        author = "Healthy Buddy — персональный помощник здоровья!",
        content = "Привет, мой будущий здоровый друг! Меня зовут Healthy Buddy, и я рад познакомиться с тобой! Ты готов присоединиться к нашему сообществу здоровых и счастливых людей? Здесь ты найдешь мотивацию и поддержку, сможешь вести дневник питания, считать шаги и получать полезные советы каждый день. Я стану твоим личным помощником на пути к прекрасному самочувствию и энергии на каждый день! Давай вместе сделаем нашу жизнь ярче, приятнее и полезнее! Ждем тебя в команде здоровых и активных ребят!",
        published = "11 марта в 08:06",
        likedByMe = false,
        likes = 8999,
        shares = 299,
        views = 89999
        ),
        Post(
            id = 2,
            author = "Healthy Buddy — персональный помощник здоровья!",
            content = "Вышел новый трек-лист для тренировок! Заходи на сайт и слушай!",
            published = "12 марта в 10:15",
            likedByMe = true,
            likes = 342,
            shares = 89,
            views = 2300
        ),
        Post(
            id = 3,
            author = "Healthy Buddy — персональный помощник здоровья!",
            content = "Что новенького у нас? Смотри наши обновления на нашем сайте!",
            published = "13 марта в 09:42",
            likedByMe = false,
            likes = 1250,
            shares = 420,
            views = 8900
        ),
        Post(
            id = 4,
            author = "Healthy Buddy — персональный помощник здоровья!",
            content = "Анонсированы новые возможности для наших спортсменов: Переходи по ссылке!",
            published = "14 марта в 08:00",
            likedByMe = false,
            likes = 5678,
            shares = 1234,
            views = 45000
        )
    )
    // MutableLiveData, который можно изменять
    private val _data = MutableLiveData(posts)
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
    }
}

