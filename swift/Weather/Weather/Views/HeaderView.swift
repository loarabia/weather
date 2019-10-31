//
//  HeaderView.swift
//  Weather
//
//  Created by Adrian Hall on 10/30/19.
//  Copyright © 2019 Adrian Hall. All rights reserved.
//

import SwiftUI
import Foundation

struct HeaderView: View {
    var city: String
    var timestamp: Date
    
    var body: some View {
        VStack {
            Text(city.uppercased())
                .font(.title)
                .fontWeight(.light)
            Text("HALP!")
                .foregroundColor(.gray)
        }
    }
}

struct HeaderView_Previews: PreviewProvider {
    static var previews: some View {
        HeaderView(
            city: "New York City",
            timestamp: Date()
        )
    }
}
