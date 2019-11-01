import SwiftUI

func formatDate(_ date: Date) -> String {
    let dateFormatter = DateFormatter()
    dateFormatter.dateFormat = "EEE, d MMM"
    return dateFormatter.string(from: date)
}

struct Header: View {
    let city: String
    let country: String
    let date: Date
    
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Group {
                    Text(self.city + ",")
                        .font(.title)
                    Text(self.country)
                        .font(.title)
                    Text(formatDate(self.date))
                        .font(.subheadline)
                        .fontWeight(.light)
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
        Header(
            city: "London",
            country: "United Kingdom",
            date: Date()
        ).background(Palette.color1)
    }
}
#endif
