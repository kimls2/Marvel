package me.yisuk.kim.marvel.data

class DatabaseTxRunner(private val db: MarvelDatabase) {
    fun runInTransaction(run: () -> Unit) = db.runInTransaction(run)
}