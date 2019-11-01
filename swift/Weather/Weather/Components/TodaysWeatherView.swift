import SwiftUI

struct TodaysWeatherView: View {
    var data: WeatherPoint
    
    var body: some View {
        VStack {
            Text("Today")
                .font(.largeTitle)
                .foregroundColor(.white)
            HStack {
                Image(systemName: systemWeatherIcon(data.type))
                    .resizable()
                    .foregroundColor(systemWeatherColor(data.type))
                    .frame(width: 72, height: 72)
                    .padding(.trailing)
                Text(String(data.temp) + "°")
                    .font(Font.system(size: 64))
                    .fontWeight(.bold)
                    .foregroundColor(.white)
                    .padding(.leading)
            }
            Text(weatherDescription(data.type))
                .font(.title)
                .fontWeight(.light)
                .foregroundColor(.white)
        }
    }
}

#if DEBUG
struct TodaysWeatherView_Previews: PreviewProvider {
    static var previews: some View {
        TodaysWeatherView(data: WeatherData().currentWeather)
            .background(Palette.color4)
    }
}
#endif
