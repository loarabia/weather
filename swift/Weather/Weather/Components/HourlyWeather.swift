import SwiftUI

struct HourlyWeather: View {
    let data: Hourly
    
    var body: some View {
        let timeString: String = data.hour <= 12 ? "AM" : "PM"
        
        return VStack {
            Text(String(data.hour) + timeString)
                .font(.caption)
                .padding(.top)
            Image(systemName: data.icon)
                .resizable()
                .padding(14)
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
        HourlyWeather(data: hourlyTestData[0])
    }
}
#endif
