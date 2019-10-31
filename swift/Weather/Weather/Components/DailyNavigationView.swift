import SwiftUI

struct DailyNavigationView: View {
    var body: some View {
        HStack {
            Text("Today")
                .foregroundColor(.white)
                .padding(.trailing)
            Text("Tomorrow")
                .foregroundColor(Palette.color2)
                .padding(.trailing)
            Text("Next 7 days")
                .foregroundColor(Palette.color2)
            Image(systemName: "arrow.right")
                .foregroundColor(Palette.color2)
            Spacer()
        }.padding(.bottom)
    }
}

struct DailyNavigationView_Previews: PreviewProvider {
    static var previews: some View {
        DailyNavigationView()
            .background(Palette.color4)
    }
}
