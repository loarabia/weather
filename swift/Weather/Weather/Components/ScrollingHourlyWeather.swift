import SwiftUI

struct ScrollingHourlyWeather: View {
    var data: [WeatherPoint]
    
    var body: some View {
        ScrollView(.horizontal) {
            HStack(spacing: 10) {
                ForEach(data, id: \.timestamp) { data in
                    HourlyWeather(data: data)
                }
            }
            .padding()
            .background(Palette.color4)
            .cornerRadius(32)
        }.padding(.bottom)
    }
}

struct ScrollingHourlyWeather_Previews: PreviewProvider {
    static var previews: some View {
        ScrollingHourlyWeather(data: WeatherData().hourlyToday)
    }
}
