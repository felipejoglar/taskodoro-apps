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


import Taskodoro
import Combine

class TasksViewModel: ObservableObject {
    @Published private(set) var tasks = [Task]()
    
    private let tasksLoader: AnyPublisher<[Task], Error>
    private var cancellable: Cancellable?
    
    init(_ tasksLoader: AnyPublisher<[Task], Error>) {
        self.tasksLoader = tasksLoader
    }
    
    func getTasks() {
        cancellable = tasksLoader
            .sink(
                receiveCompletion: { [weak self] completion in
                    switch completion {
                    case .finished: break
                    
                    case let .failure(error):
                        self?.tasks = [Task(
                            id: "",
                            title: "An error occurred: \(error)",
                            description: "A task description",
                            priority: .medium,
                            isCompleted: false,
                            createdAt: 0,
                            updatedAt: 0
                        )]
                    }
                }, receiveValue: { [weak self] tasks in
                    self?.tasks = tasks
                })
    }
    
    func onClear() {
        cancellable?.cancel()
        cancellable = nil
    }
}

