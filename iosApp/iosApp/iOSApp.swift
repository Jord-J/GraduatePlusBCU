import SwiftUI
import FirebaseCore

@main
struct iOSApp: App {

    init()
    {
        FirebaseApp.Configure()
    }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}