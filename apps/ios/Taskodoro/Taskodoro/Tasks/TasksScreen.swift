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

import SwiftUI
import Tasks

struct TasksScreen: View {
    @ObservedObject var viewModel: TasksViewModel
    
    var body: some View {
        TasksContent(tasks: viewModel.tasks)
        .onAppear {
            viewModel.getTasks()
        }
    }
}

struct TasksContent: View {
    let tasks: [Task]
    
    var body: some View {
        List(tasks) { task in
            Text(task.title)
                .padding()
        }
        .listStyle(.plain)
    }
}

extension Task : Identifiable {}


