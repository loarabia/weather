import Foundation
import SwiftUI

enum WeatherType {
    case clear
    case cloud
    case fog
    case hail
    case haze
    case heavyrain
    case lightrain
    case rain
    case sleet
    case snow
    case sun
    case thunderstorm
    case wind
}

func systemWeatherIcon(_ type: WeatherType) -> String {
    switch type {
    case .clear:        return "sun.min"
    case .cloud:        return "cloud"
    case .fog:          return "cloud.fog"
    case .hail:         return "cloud.hail"
    case .haze:         return "sun.haze"
    case .heavyrain:    return "cloud.heavyrain"
    case .lightrain:    return "cloud.drizzle"
    case .rain:         return "cloud.rain"
    case .sleet:        return "cloud.sleet"
    case .snow:         return "snow"
    case .sun:          return "sun.max"
    case .thunderstorm: return "cloud.bolt.rain"
    case .wind:         return "wind"
    }
}

func systemWeatherColor(_ type: WeatherType) -> Color {
    switch type {
    case .cloud:        return Palette.color2
    case .fog:          return Palette.color2
    case .heavyrain:    return .gray
    case .lightrain:    return .gray
    case .rain:         return .gray
    case .sun:          return .yellow
    default:            return .white
    }
}

func weatherDescription(_ type: WeatherType) -> String {
    switch type {
    case .clear:        return "Clear"
    case .cloud:        return "Cloudy"
    case .fog:          return "Fog"
    case .hail:         return "Hail"
    case .haze:         return "Haze"
    case .heavyrain:    return "Heavy Rain"
    case .lightrain:    return "Drizzle"
    case .rain:         return "Rain"
    case .sleet:        return "Sleet"
    case .snow:         return "Snow"
    case .sun:          return "Sunny"
    case .thunderstorm: return "Thunderstorms"
    case .wind:         return "Windy"
    }
}
