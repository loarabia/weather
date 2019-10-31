import SwiftUI

struct TodaysWeather: View {
    var body: some View {
        ZStack {
            BackgroundGradient()
            VStack {
                Header()
                Spacer()
                TodaysWeatherView()
                Spacer()
                DailyNavigationView()
                ScrollingHourlyWeather()
            }.padding([.leading, .bottom])
        }
    }
}

#if DEBUG
struct WeatherScene_Previews: PreviewProvider {
    static var previews: some View {
        TodaysWeather()
    }
}
#endif
