package main

import (
	"testing"
)

func TestRaceWinnerCar1(t *testing.T) {
	car1 := NewCar("Ferrari", 150, 100)
	car2 := NewCar("Toyota", 80, 100)

	expected := "Ferrari Won"
	actual := Race(car1, car2)

	if actual != expected {
		t.Errorf("expected %s but got %s", expected, actual)
	}
}

func TestRaceNotEnoughFuel(t *testing.T) {
	car1 := NewCar("Toyota", 80, 9)
	car2 := NewCar("Ferrari", 150, 20)

	expected := "Toyota Not enough fuel"
	actual := Race(car1, car2)

	if actual != expected {
		t.Errorf("expected %s but got %s", expected, actual)
	}
}
