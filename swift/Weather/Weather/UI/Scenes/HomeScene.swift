import SwiftUI

struct HomeScene: View {
    @EnvironmentObject var store: AppStore
    @State var selectedTab: Int = 0
    
    var body: some View {
        VStack {
            ZStack {
                HeaderBackground().frame(height: 76)
                HStack {
                    Text(self.title())
                        .font(.system(size: 32))
                        .fontWeight(.regular)
                        .foregroundColor(.white)
                }
            }
            TabView(selection: $selectedTab) {
                FavoritesScene()
                    .tabItem {
                        Image(systemName: "star")
                        Text("Favorites")
                }.tag(0)
                SearchScene()
                    .tabItem {
                        Image(systemName: "magnifyingglass")
                        Text("Search")
                }.tag(1)
            }
        }
    }
    
    private func title() -> String {
        switch (self.selectedTab) {
        case 0: return "Favorites"
        case 1: return "Search"
        default: return "Error"
        }
    }
}

#if DEBUG
struct HomeScene_Previews: PreviewProvider {
    static var previews: some View {
        HomeScene().environmentObject(AppStore(true))
    }
}
#endif
