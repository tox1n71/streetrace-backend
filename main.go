package main

type Car struct {
	Name  string
	Speed int
	Fuel  int
}

func Race(c1 *Car, c2 *Car) string {
	if !hasFuelForRace(c1) {
		return c1.Name + " Not enough fuel"
	}
	if !hasFuelForRace(c2) {
		return c2.Name + " Not enough fuel"
	}
	c1.Fuel -= 10
	c2.Fuel -= 10
	if c1.Speed > c2.Speed {
		return c1.Name + " Won"
	} else {
		return c2.Name + " Won"
	}
}
func hasFuelForRace(c *Car) bool {
	if c.Fuel < 5 {
		return false
	}
	return true
}

// NewCar создает новую машину
func NewCar(name string, speed int, fuel int) *Car {
	return &Car{Name: name, Speed: speed, Fuel: fuel}
}
