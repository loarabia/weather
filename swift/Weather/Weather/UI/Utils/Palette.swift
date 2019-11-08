import SwiftUI

// Extension method to convert a CSS color (expressed as 0xABCDEF) into
// a Color resource.
extension Color {
    static func fromRGB(_ rgb: UInt) -> Color {
        return Color(
            red: Double((rgb & 0xFF0000) >> 16) / 255.0,
            green: Double((rgb & 0x00FF00) >> 8) / 255.0,
            blue: Double(rgb & 0x0000FF) / 255.0
        )
    }
}

// Static listing of all gradients and colors we use.
class Palette {
    // Gradients
    static let detailBackground = LinearGradient(
        gradient: Gradient(colors: [
            Color.fromRGB(0x3867D5), Color.fromRGB(0x81C7F5)
        ]), startPoint: .topLeading, endPoint: .bottomTrailing)
}

#if DEBUG
struct GradientPreview: View {
    let title: String
    let gradient: LinearGradient
    
    var body: some View {
        VStack {
            Text(title)
            Rectangle().fill(gradient)
                .frame(width: 100, height: 50)
                .border(Color.black)
        }
    }
}

struct Palette_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            Text("Gradients").font(.title).padding()
            HStack {
                GradientPreview(title: "detail", gradient: Palette.detailBackground)
            }
            Spacer()
        }
    }
}
#endif
