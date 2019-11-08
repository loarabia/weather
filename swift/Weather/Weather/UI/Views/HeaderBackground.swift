import SwiftUI

struct HeaderBackground: View {
    var body: some View {
        Rectangle()
            .fill(Palette.detailBackground)
            .edgesIgnoringSafeArea(.top)
    }
}

struct HeaderBackground_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            ZStack {
                HeaderBackground()
            }.frame(height: 100)
            Spacer()
        }
    }
}
