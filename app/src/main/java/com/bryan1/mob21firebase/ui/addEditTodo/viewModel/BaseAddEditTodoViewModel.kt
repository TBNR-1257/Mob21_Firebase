package com.bryan1.mob21firebase.ui.addEditTodo.viewModel

import com.bryan1.mob21firebase.data.repo.TodosRepo
import com.bryan1.mob21firebase.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseAddEditTodoViewModel(): BaseViewModel() {
    protected val _finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val finish: SharedFlow<Unit> = _finish

    abstract fun submit(title: String, desc: String)

}