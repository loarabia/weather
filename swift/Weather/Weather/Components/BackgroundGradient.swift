import SwiftUI

struct BackgroundGradient: View {
    var body: some View {
        Rectangle()
            .fill(LinearGradient(
                gradient: Gradient(colors: [ Palette.color1, Palette.color4 ]),
                startPoint: .top, endPoint: .bottom
            ))
            .edgesIgnoringSafeArea(.all)
    }
}

#if DEBUG
struct BackgroundGradient_Previews: PreviewProvider {
    static var previews: some View {
        BackgroundGradient()
    }
}
#endif
