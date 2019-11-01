import SwiftUI

struct HourlyWeather: View {
    let data: WeatherPoint
    
    var body: some View {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "ha"
        
        return VStack {
            Text(dateFormatter.string(from: data.timestamp))
                .font(.caption)
                .padding(.top)
            Image(systemName: systemWeatherIcon(data.type))
                .resizable()
                .frame(width: 32, height:32)
            Text(String(data.temp) + "°")
                .font(.callout)
                .padding(.bottom)
        }
        .frame(width: 64, height: 140)
        .background(Palette.color1)
        .foregroundColor(.white)
        .cornerRadius(32)
    }
}

#if DEBUG
struct HourlyWeather_Previews: PreviewProvider {
    static var previews: some View {
        HourlyWeather(data: WeatherData().currentWeather)
    }
}
#endif
