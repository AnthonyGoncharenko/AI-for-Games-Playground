use std::cmp::Ordering;

use derive_getters::Getters;
use rand::Rng;

#[derive(Getters, Hash, Eq, PartialOrd, PartialEq, Clone)]
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
}
impl Ord for Edge {
    fn cmp(&self, other: &Self) -> std::cmp::Ordering {
        if self.weight > other.weight {
            Ordering::Greater
        } else if self.weight < other.weight {
            Ordering::Less
        } else {
            Ordering::Equal
        }
    }
}
