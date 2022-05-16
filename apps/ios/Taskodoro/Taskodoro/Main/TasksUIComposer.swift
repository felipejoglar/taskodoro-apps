//
//  Copyright 2022 Felipe Joglar
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//


import Combine
import Tasks
import Storage

final class TasksUIComposer {
    private init() {}
        
    static func makeTasksScreen() -> TasksScreen {
        let dispatchers = DefaultDispatcherProvider()
        let taskLocalDataSource = InMemoryTaskLocalDataSourceAdapter()
        let taskRepository = TaskRepository(localDataSource: taskLocalDataSource)
        let tasksLoader = deferredFuture(taskRepository.getTasks)
            .subscribe(on: dispatchers.io)
            .receive(on: dispatchers.main)
            .eraseToAnyPublisher()
    
        let taskViewModel = TasksViewModel(tasksLoader)
        
        return TasksScreen(viewModel: taskViewModel)
    }
}

private final class InMemoryTaskLocalDataSourceAdapter: TaskLocalDataSource {
    private let dataSource = InMemoryTaskDataSource()
    
    public func getAllTasks() -> [Task] {
        dataSource.getAllTasks().map {
            Task(id: $0.id, title: $0.title)
        }
    }
}
