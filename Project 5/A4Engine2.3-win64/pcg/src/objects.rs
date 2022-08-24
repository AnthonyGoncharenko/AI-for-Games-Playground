use std::fmt;

#[derive(Copy, Clone, Debug)]
pub enum Objects {
    LANDTILE = 31,
    HOUSE = 42,
    BIGTREETILE = 45,
    SMALLTREETILE = 46,
    ROCK = 47,
    EMPTY = 0,
}

impl fmt::Display for Objects {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "{}", *self as u8)
    }
}
