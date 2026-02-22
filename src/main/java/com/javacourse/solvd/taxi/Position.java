package com.javacourse.solvd.taxi;

public record Position(double latitude, double longitude) {

    public Position setLatitude(double latitude) {
        return new Position(latitude, this.longitude);
    }
    public Position setLongitude(double longitude) {
        return new Position(this.latitude, longitude);
    }
}
