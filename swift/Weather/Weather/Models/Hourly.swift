import Foundation

struct Hourly {
    let hour: Int
    let temp: Int
    let icon: String
}

#if DEBUG
let hourlyTestData: [Hourly] = [
    Hourly(hour: 9, temp: 15, icon: "cloud.rain"),
    Hourly(hour: 10, temp: 18, icon: "cloud"),
    Hourly(hour: 11, temp: 20, icon: "sun.max"),
    Hourly(hour: 12, temp: 22, icon: "sun.max"),
    Hourly(hour: 13, temp: 23, icon: "sun.max"),
    Hourly(hour: 14, temp: 23, icon: "cloud"),
    Hourly(hour: 15, temp: 22, icon: "cloud"),
    Hourly(hour: 16, temp: 21, icon: "cloud"),
    Hourly(hour: 17, temp: 20, icon: "wind"),
    Hourly(hour: 18, temp: 19, icon: "wind")
]
#endif
