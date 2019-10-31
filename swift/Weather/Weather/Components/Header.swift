import SwiftUI

struct Header: View {
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Group {
                    Text("London,").font(.title)
                    Text("United Kingdom").font(.title)
                    Text("Sat, 6 Aug").font(.subheadline).fontWeight(.light)
                }
                .foregroundColor(.white)
                .padding(.bottom, 6)
            }
            Spacer()
        }
    }
}

#if DEBUG
struct Header_Previews: PreviewProvider {
    static var previews: some View {
        Header()
    }
}
#endif
