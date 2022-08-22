fn main() {
    use edge::Edge;
    let edge = Edge::new(5, 19);
    println!("{}", edge.repr());
}

mod objects {
    pub enum Objects {
        LANDTILE = 31,
        HOUSE = 42,
        BIGTREETILE = 45,
        SMALLTREETILE = 46,
        ROCK = 47,
    }
}

mod edge {
    use std::fmt;

    use derive_getters::Getters;
    use rand::Rng;

    #[derive(Getters)]
    pub struct Edge {
        start: i32,
        end: i32,
        weight: i32,
    }

    impl Edge {
        pub fn new(start: i32, end: i32) -> Edge {
            Edge {
                start,
                end,
                weight: rand::thread_rng().gen_range(1..=10),
            }
        }
        pub fn repr(&self) -> String {
            fmt::format(format_args!(
                "start: {}, end: {}, weight: {}",
                self.start(),
                self.end(),
                self.weight()
            ))
        }
    }
}
