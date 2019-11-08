import Foundation

struct Weather: Codable, Identifiable {
    var id: Date { return time }
    
    var time: Date
    var icon: String
}
