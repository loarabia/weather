import SwiftUI

struct TodaysWeatherView: View {
    var body: some View {
        VStack {
            Text("Today")
                .font(.largeTitle)
                .foregroundColor(.white)
            HStack {
                Image(systemName: "sun.max")
                    .resizable()
                    .foregroundColor(.yellow)
                    .frame(width: 72, height: 72)
                Text("22°")
                    .font(Font.system(size: 64))
                    .fontWeight(.bold)
                    .foregroundColor(.white)
            }
            Text("Sunny")
                .font(.title)
                .fontWeight(.light)
                .foregroundColor(.white)
        }
    }
}

#if DEBUG
struct TodaysWeatherView_Previews: PreviewProvider {
    static var previews: some View {
        TodaysWeatherView().background(Palette.color4)
    }
}
#endif
