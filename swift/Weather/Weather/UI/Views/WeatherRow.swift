import SwiftUI

struct WeatherRow: View {
    @ObservedObject var city: City
    
    init(_ city: City) { self.city = city }
    
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(self.getCityName())
                    .font(.system(size: 24))
                Text(self.getCityCoords())
                    .font(.caption)
                    .fontWeight(.light)
            }
            Spacer()
        }
    }
    
    private func getCityName() -> String {
        guard let name = city.name else { return "--" }
        guard let region = city.region else { return name }
        return "\(name), \(region)"
    }
    
    private func getCityCoords() -> String {
        guard let lat = city.latitude  else { return "--" }
        guard let lng = city.longitude else { return "++" }
        return String(format: "%.5f, %.5f", lat, lng)
    }
}

struct WeatherRow_Previews: PreviewProvider {
    static var previews: some View {
        WeatherRow(debugCity)
    }
}
