//
//  SampleViewModelObservable.swift
//  iosApp
//
//  Created by Дмитрий on 12.09.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import sample

class ContentViewModel : ObservableObject {
    
    init() {
        aa()
    }
    
    private let sampleVm = SampleViewModel()
    
    @State var state: SampleState = SampleState(currentValue: 0, isProgress: false)
    
    
    
    func aa() {
        sampleVm.stateFlow.subscribe(block: { state in
            self.state = state
        })
        
        
    }
}
