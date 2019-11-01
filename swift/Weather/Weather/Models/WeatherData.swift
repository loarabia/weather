import SwiftUI
import Combine

#if DEBUG
func randomWeather() -> WeatherType {
    let wt: [WeatherType] = [
        WeatherType.clear,
        WeatherType.cloud,
        WeatherType.fog,
        WeatherType.hail,
        WeatherType.haze,
        WeatherType.heavyrain,
        WeatherType.lightrain,
        WeatherType.rain,
        WeatherType.sleet,
        WeatherType.snow,
        WeatherType.sun,
        WeatherType.thunderstorm,
        WeatherType.wind
    ]
    let randomInt = Int.random(in: 0 ..< wt.count)
    return wt[randomInt]
}

func getHourlyWeather(_ date: Date) -> [WeatherPoint] {
    let hour = Calendar.current.component(.hour, from: date) + 1
    if (hour == 23) {
        // There is no more data for this day
        return []
    }
    var result = [WeatherPoint]()

    for hr in hour...23 {
        let weatherPoint = WeatherPoint(
            timestamp: Calendar.current.date(
                bySetting: .hour, value: hr, of: date)!,
            temp: Int.random(in: 12 ..< 25),
            type: randomWeather())
        result.append(weatherPoint)
    }
    return result
}
#endif

final class WeatherData: ObservableObject {
    @Published var cityName: String = "--Loading--"
    @Published var countryName: String = ""
    @Published var timestamp: Date = Date()
    @Published var currentWeather: WeatherPoint
        = WeatherPoint(timestamp: Date(), temp: 0, type: .clear)
    @Published var hourlyToday: [WeatherPoint] = []

    init() {
        #if DEBUG
        cityName = "London"
        countryName = "United Kingdom"
        timestamp = Date()
        currentWeather = WeatherPoint(
            timestamp: Date(),
            temp: Int.random(in: 12 ..< 25),
            type: randomWeather())
        hourlyToday = getHourlyWeather(Date())
        #endif
    }
}
