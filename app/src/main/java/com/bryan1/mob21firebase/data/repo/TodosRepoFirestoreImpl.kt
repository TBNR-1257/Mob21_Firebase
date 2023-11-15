package com.bryan1.mob21firebase.data.repo

import android.util.Log
import com.bryan1.mob21firebase.core.FIREBASE_FIRESTORE
import com.bryan1.mob21firebase.core.TAG
import com.bryan1.mob21firebase.data.model.Todo
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class TodosRepoFirestoreImpl(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
): TodosRepo {

    override fun getAllTodos() = callbackFlow {
        val listener = db.addSnapshotListener { value, error ->
            if(error != null) {
                Log.d(TAG, error.message.toString())
                throw error
            }
            val todos = mutableListOf<Todo>()
            value?.documents?.let {items ->
                for(item in items) {
                    item.data?.let {
                        it["id"] = item.id
                        todos.add(Todo.fromHashMap(it))
                    }
                }
                trySend(todos)
            }
        }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun insert(todo: Todo) {
        val id = db.document().id
        db.document(id).set(todo.toHashMap()).await()
    }

    override suspend fun getTodo(id: String): Todo? {
        val snapshot = db.document(id).get().await()
        return snapshot.data?.let {
            it["id"] = snapshot.id
            Todo.fromHashMap(it)
        }
    }
    override suspend fun update(id: String, todo: Todo) {
        Log.d(TAG, "id: $id")
        db.document(id).set(todo.toHashMap()).await()
    }


    override suspend fun delete(id: String) {
        db.document(id).delete().await()
    }


    override suspend fun storeUsername(id: String, name: String) {
        val userDb = FirebaseFirestore.getInstance().collection("users")
        userDb.document(id).set(hashMapOf("name" to name)).await()
    }

    override suspend fun greet(): String {
        val publicDb = FirebaseFirestore.getInstance().collection("public")
        val snapshot = publicDb.document("greeting").get().await()
        return snapshot.data?.let {
            it["msg"].toString()
        } ?: "Hello User"
    }


}