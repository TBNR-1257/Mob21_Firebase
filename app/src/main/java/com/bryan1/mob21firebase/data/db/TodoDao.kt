package com.bryan1.mob21firebase.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bryan1.mob21firebase.data.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("select * from todo")
    fun getAllTodos(): Flow<List<Todo>>

    @Query("select * from todo where _id = :id")
    fun getById(id: Int): Todo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: Todo)

    @Query("delete from todo where _id = :id")
    fun delete(id: Int)

}