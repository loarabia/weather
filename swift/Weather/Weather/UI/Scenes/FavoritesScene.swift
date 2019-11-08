import SwiftUI

struct FavoritesScene: View {
    @EnvironmentObject var store: AppStore
    
    var body: some View {
        NavigationView {
            List {
                ForEach(store.favoriteCities) { city in
                    WeatherRow(city)
                }
            }
            .navigationBarTitle("")
            .navigationBarHidden(true)
        }
    }
}

#if DEBUG
struct FavoritesScene_Previews: PreviewProvider {
    static var previews: some View {
        FavoritesScene().environmentObject(AppStore(true))
    }
}
#endif
