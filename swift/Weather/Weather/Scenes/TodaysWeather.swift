import SwiftUI

struct TodaysWeather: View {
    @EnvironmentObject var weatherData: WeatherData
    
    var body: some View {
        ZStack {
            BackgroundGradient()
            VStack {
                Header(
                    city: weatherData.cityName,
                    country: weatherData.countryName,
                    date: weatherData.timestamp
                )
                Spacer()
                TodaysWeatherView(data: weatherData.currentWeather)
                Spacer()
                DailyNavigationView()
                ScrollingHourlyWeather(data: weatherData.hourlyToday)
            }.padding([.leading, .bottom])
        }
    }
}

#if DEBUG
struct WeatherScene_Previews: PreviewProvider {
    static var previews: some View {
        TodaysWeather()
            .environmentObject(WeatherData())
    }
}
#endif
