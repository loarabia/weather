import Foundation
import SwiftUI
import MapKit
import Combine

#if DEBUG
var debugCity = City("Seattle", "USA", 47.60621, -122.33207)
#endif

class AppStore: ObservableObject {
    @Published var favoriteCities: [City] = []
    
    init(_ bypassLoading: Bool = false) {
        if (bypassLoading) {
            favoriteCities = [
                City("Seattle", "USA", 47.60621, -122.33207),
                City("London", "UK", 51.507351, -0.127758)
            ]
        }
    }
}

class City: ObservableObject, Identifiable {
    var id = UUID()
    @Published var name: String? = nil
    @Published var region: String? = nil
    @Published var latitude: Double? = nil
    @Published var longitude: Double? = nil
    @Published var weather: Weather? = nil
    
    init(_ place: MKPlacemark) {
        self.name      = place.locality
        self.region    = place.country
        self.latitude  = place.coordinate.latitude
        self.longitude = place.coordinate.longitude
    }
    
    init(_ name: String, _ region: String, _ lat: Double, _ long: Double) {
        self.name      = name
        self.region    = region
        self.latitude  = lat
        self.longitude = long
    }
}
