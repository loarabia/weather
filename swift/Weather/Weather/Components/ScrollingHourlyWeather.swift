import SwiftUI

struct ScrollingHourlyWeather: View {
    var body: some View {
        ScrollView(.horizontal) {
            HStack(spacing: 10) {
                ForEach(hourlyTestData, id: \.hour) { data in
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
        ScrollingHourlyWeather()
    }
}
