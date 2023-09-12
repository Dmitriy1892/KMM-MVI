import SwiftUI
import sample

struct ContentView: View {
	let greet = "Text"
    
    let pub = ContentViewModel().state

	var body: some View {
        
		Text(greet)
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
