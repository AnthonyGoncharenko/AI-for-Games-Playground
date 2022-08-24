mod edge;
mod graph;
mod objects;
mod pcg_maker;

fn main() {
    let (start_x, start_y, map) = generate_map();
    PCG::write_to_file(start_x, start_y, &map);
}

use crate::{objects::Objects, pcg_maker::PCG};
use rand::Rng;
pub fn generate_map() -> (i32, i32, [[Objects; 17]; 17]) {
    const HEIGHT: usize = 8;
    const WIDTH: usize = 8;
    let mut start_x: i32 = -1;
    let mut start_y: i32 = -1;
    const NEW_HEIGHT: usize = 2 * HEIGHT + 1;
    const NEW_WIDTH: usize = 2 * WIDTH + 1;
    let mut map = [[Objects::EMPTY; NEW_WIDTH]; NEW_HEIGHT];

    for i in 0..NEW_WIDTH {
        for j in 0..NEW_HEIGHT {
            if i == 0 || j == 0 || i == NEW_WIDTH - 1 || j == NEW_HEIGHT - 1 {
                map[i][j] = Objects::BIGTREETILE;
            } else if i % 2 == 1 && j % 2 == 1 {
                map[i][j] = Objects::LANDTILE;
            } else {
                let r1: i32 = rand::thread_rng().gen_range(1..=100);

                if r1 <= 5 {
                    map[i][j] = Objects::LANDTILE;
                } else if r1 <= 15 {
                    map[i][j] = Objects::HOUSE;
                } else if r1 < 25 {
                    map[i][j] = Objects::ROCK;
                } else {
                    map[i][j] = Objects::SMALLTREETILE;
                }
            }
        }
    }

    let mut g = graph::Graph::new(WIDTH as i32, HEIGHT as i32);

    for edge in g.kruskal().into_sorted_iter() {
        let row1: i32 = 2 * (*edge.0.start() / WIDTH as i32) + 1;
        let col1: i32 = 2 * (*edge.0.start() % WIDTH as i32) + 1;

        if start_x == -1 {
            start_x = col1;
            start_y = row1;
        }

        let row2: i32 = 2 * (*edge.0.end() / WIDTH as i32) + 1;
        let col2: i32 = 2 * (*edge.0.end() % WIDTH as i32) + 1;

        if col2 > col1 {
            map[row1 as usize][(col1 + 1) as usize] = Objects::LANDTILE;
        } else if row2 > row1 {
            map[(row1 + 1) as usize][col1 as usize] = Objects::LANDTILE;
        }
    }
    start_x *= 32;
    start_y *= 32;

    (start_x, start_y, map)
}
