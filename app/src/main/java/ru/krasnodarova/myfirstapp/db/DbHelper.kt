package ru.krasnodarova.myfirstapp.db
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import ru.krasnodarova.myfirstapp.db.PostContract.Columns
import android.provider.BaseColumns._ID

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "myfirstapp.db"
        private const val DATABASE_VERSION = 1

        // SQL для создания таблицы
        private val SQL_CREATE_POSTS =
            "CREATE TABLE ${PostContract.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Columns.AUTHOR} TEXT NOT NULL," +
                    "${Columns.AUTHOR_ID} INTEGER NOT NULL," +
                    "${Columns.CONTENT} TEXT NOT NULL," +
                    "${Columns.PUBLISHED} TEXT NOT NULL," +
                    "${Columns.LIKED_BY_ME} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.LIKES} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.SHARES} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.VIEWS} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.VIDEO} TEXT" +
                    ")"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Создаем таблицу при первом запуске
        db.execSQL(SQL_CREATE_POSTS)

        // Здесь можно добавить начальные данные
        insertInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // При обновлении версии удаляем старую таблицу и создаем новую
        // В реальном проекте здесь должна быть миграция данных
        db.execSQL("DROP TABLE IF EXISTS ${PostContract.TABLE_NAME}")
        onCreate(db)
    }

    private fun insertInitialData(db: SQLiteDatabase) {
        // Вставляем начальные посты для демонстрации
        val contentValues = android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Книжный клуб «Переплёт»")
            put(Columns.AUTHOR_ID, 2)
            put(Columns.CONTENT, "Друзья, на этой неделе мы погружаемся в мир великого романа Михаила Булгакова — «Мастер и Маргарита»! Делитесь своими любимыми цитатами, впечатлениями и теориями. Какая сцена вас тронула больше всего? Присоединяйтесь к обсуждению в комментариях!")
            put(Columns.PUBLISHED, "12 марта в 08:06")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 1420)
            put(Columns.SHARES, 310)
            put(Columns.VIEWS, 9800)
            putNull(Columns.VIDEO)
        }
        db.insert(PostContract.TABLE_NAME, null, contentValues)

        // Второй пост с видео
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Healthy Buddy — персональный помощник здоровья!")
            put(Columns.AUTHOR_ID, 3)
            put(Columns.CONTENT, "Вышел новый трек-лист для тренировок! Заходи на сайт и слушай!")
            put(Columns.PUBLISHED, "12 марта в 10:15")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 3042)
            put(Columns.SHARES, 89)
            put(Columns.VIEWS, 23000)
            put(Columns.VIDEO, "https://www.youtube.com/watch?v=ojVA7Rs4gtI")
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        // 3. Пост от ТехноМир
        val contentValues3 = android.content.ContentValues().apply {
            put(Columns.AUTHOR, "ТехноМир")
            put(Columns.AUTHOR_ID, 4)
            put(Columns.CONTENT, "Представляем вашему вниманию топ-5 гаджетов, которые, по мнению экспертов, изменят нашу повседневную жизнь в 2026 году. В нашем обзоре — умные очки с дополненной реальностью, компактные зарядные станции и многое другое. Подробности и обзоры — по ссылке в профиле!")
            put(Columns.PUBLISHED, "13 марта в 09:42")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 2110)
            put(Columns.SHARES, 470)
            put(Columns.VIEWS, 15300)
        }
        db.insert(PostContract.TABLE_NAME, null, contentValues3)

        // 4. Пост от Киномания
        val contentValues4 = android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Киномания")
            put(Columns.AUTHOR_ID, 5)
            put(Columns.CONTENT, "Выходные — идеальное время для кино! Мы собрали для вас подборку самых интересных новинок марта: от драм до комедий и фантастики. Какой фильм выберете вы? Пишите в комментариях свои варианты!")
            put(Columns.PUBLISHED, "14 марта в 08:00")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 1890)
            put(Columns.SHARES, 520)
            put(Columns.VIEWS, 18900)
        }
        db.insert(PostContract.TABLE_NAME, null, contentValues4)
        // Пример для поста id=5 (Психология и я):
        val contentValues5 = android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Психология и я")
            put(Columns.AUTHOR_ID, 6)
            put(Columns.CONTENT, "Стресс — частый спутник современной жизни. Мы собрали 7 простых и эффективных способов, которые помогут вам справиться с напряжением и вернуть гармонию. Попробуйте уже сегодня!")
            put(Columns.PUBLISHED, "14 марта в 15:40")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 2340)
            put(Columns.SHARES, 390)
            put(Columns.VIEWS, 11200)
            putNull(Columns.VIDEO) // Нет видео
        }
        db.insert(PostContract.TABLE_NAME, null, contentValues5)
        // Пост 6: ГастроТур
        val contentValues6 = android.content.ContentValues().apply {
            put(Columns.AUTHOR, "ГастроТур")
            put(Columns.AUTHOR_ID, 7)
            put(Columns.CONTENT, "Гастрономическая Москва не перестаёт удивлять! В нашем новом обзоре — лучшие рестораны марта: от уютных семейных заведений до изысканных авторских кухонь. Куда сходить — решать вам!")
            put(Columns.PUBLISHED, "15 марта в 05:10")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 1560)
            put(Columns.SHARES, 340)
            put(Columns.VIEWS, 10100)
        }
        db.insert(PostContract.TABLE_NAME, null, contentValues6)

// Пост 7: IT-новости
        val contentValues7 = android.content.ContentValues().apply {
            put(Columns.AUTHOR, "IT-новости")
            put(Columns.AUTHOR_ID, 8)
            put(Columns.CONTENT, "\tИскусственный интеллект активно внедряется в образование. Как это меняет процесс обучения и какие перспективы открывает для студентов и преподавателей? Читайте наш свежий материал!")
            put(Columns.PUBLISHED, "15 марта в 17:30")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 2030)
            put(Columns.SHARES, 450)
            put(Columns.VIEWS, 14700)
        }
        db.insert(PostContract.TABLE_NAME, null, contentValues7)

// Пост 8: Мода и стиль
        val contentValues8 = android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Мода и стиль")
            put(Columns.AUTHOR_ID, 9)
            put(Columns.CONTENT, "Весна-лето 2026 года приносит свежие идеи и яркие тренды! В нашем обзоре — главные цвета, фасоны и аксессуары сезона. Будьте в курсе модных новинок и вдохновляйтесь!")
            put(Columns.PUBLISHED, "15 марта в 20:00")
            put(Columns.LIKED_BY_ME, 1) // likedByMe = true
            put(Columns.LIKES, 2450)
            put(Columns.SHARES, 580)
            put(Columns.VIEWS, 20300)
        }
        db.insert(PostContract.TABLE_NAME, null, contentValues8)

// Пост 9: Путешествия по России
        val contentValues9 = android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Путешествия по России")
            put(Columns.AUTHOR_ID, 10)
            put(Columns.CONTENT, "Лето на Алтае — это незабываемые впечатления! Мы составили топ-5 мест, которые стоит посетить: от горных озёр до древних курганов. Планируйте свой маршрут вместе с нами!")
            put(Columns.PUBLISHED, "16 марта в 07:00")
            put(Columns.LIKED_BY_ME, 1) // likedByMe = true
            put(Columns.LIKES, 1340)
            put(Columns.SHARES, 290)
            put(Columns.VIEWS, 9200)
        }
        db.insert(PostContract.TABLE_NAME, null, contentValues9)

// Пост 10: DIY идеи
        val contentValues10 = android.content.ContentValues().apply {
            put(Columns.AUTHOR, "DIY идеи")
            put(Columns.AUTHOR_ID, 11)
            put(Columns.CONTENT, "Создайте стильный декор для дома своими руками! В нашем новом мастер-классе — пошаговая инструкция по изготовлению оригинальных украшений из подручных материалов. Вдохновляйтесь и творите!")
            put(Columns.PUBLISHED, "16 марта в 07:30")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 1920)
            put(Columns.SHARES, 430)
            put(Columns.VIEWS, 12900)
        }
        db.insert(PostContract.TABLE_NAME, null, contentValues10)

// Пост 11: Наука и открытия
        val contentValues11 = android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Наука и открытия")
            put(Columns.AUTHOR_ID, 12)
            put(Columns.CONTENT, "Учёные выяснили: качественный сон напрямую влияет на память и способность к обучению. Узнайте, как улучшить свой сон и повысить продуктивность, в нашей новой статье!")
            put(Columns.PUBLISHED, "16 марта в 12:30")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 1650)
            put(Columns.SHARES, 370)
            put(Columns.VIEWS, 11800)
        }
        db.insert(PostContract.TABLE_NAME, null, contentValues11)
    }
}