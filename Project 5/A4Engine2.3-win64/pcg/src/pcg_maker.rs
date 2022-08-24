use std::env::current_dir;
use xmlwriter::*;

use crate::objects::Objects;
use regex::Regex;
pub struct PCG {}

impl PCG {
    pub fn write_to_file(start_x: i32, start_y: i32, map: &[[Objects; 17]; 17]) -> () {
        let opt = Options {
            use_single_quote: false,
            ..Options::default()
        };
        let mut xml_writer = XmlWriter::new(opt);

        let curr_dir = current_dir().unwrap();
        let dirpath = curr_dir.as_path();

        let mut path = std::path::Path::parent(dirpath)
            .unwrap()
            .join("games")
            .join("example");
        path.push("map.xml");

        let p = std::path::Path::new(&path)
            .with_file_name("example.xml")
            .to_owned();

        let example_file = std::fs::read_to_string(&p.to_str().unwrap()).unwrap();

        let rex = Regex::new(r#" x="\d+""#).unwrap();
        let rey = Regex::new(r#" y="\d+""#).unwrap();
        rex.replace(&example_file, format!(r#" x="{}""#, start_x));
        rey.replace(&example_file, format!(r#" y="{}""#, start_y));

        std::fs::write(&p.to_str().unwrap(), example_file).unwrap();

        xml_writer.write_declaration();

        xml_writer.start_element("map");
        xml_writer.write_attribute("version", "1.0");
        xml_writer.write_attribute("orientation", "orthogonal");
        xml_writer.write_attribute("width", &map.len());
        xml_writer.write_attribute("height", &map[0].len());
        xml_writer.write_attribute("tilewidth", "32");
        xml_writer.write_attribute("tileheight", "32");
        xml_writer.start_element("properties");
        xml_writer.start_element("property");
        xml_writer.write_attribute("name", "name");
        xml_writer.write_attribute("value", "Blackrock");
        xml_writer.end_element();
        xml_writer.end_element();

        xml_writer.start_element("tileset");
        xml_writer.write_attribute("firstgrid", "1");
        xml_writer.write_attribute("name", "graphics");
        xml_writer.write_attribute("tilewidth", "32");
        xml_writer.write_attribute("tileheight", "32");
        xml_writer.start_element("image");
        xml_writer.write_attribute("source", "graphics.png");
        xml_writer.write_attribute("width", "320");
        xml_writer.write_attribute("height", "1184");
        xml_writer.end_element();
        xml_writer.end_element();

        xml_writer.start_element("layer");
        xml_writer.write_attribute("name", "Tile Layer 1");
        xml_writer.write_attribute("width", &map.len());
        xml_writer.write_attribute("height", &map[0].len());
        xml_writer.start_element("data");

        for _ in 0..map.len() {
            for _ in 0..map[0].len() {
                xml_writer.start_element("tile");
                xml_writer.write_attribute("gid", &(Objects::LANDTILE as u8));
                xml_writer.end_element();
            }
        }
        xml_writer.end_element();
        xml_writer.end_element();

        xml_writer.start_element("layer");
        xml_writer.write_attribute("name", "Tile Layer 2");
        xml_writer.write_attribute("width", &map.len());
        xml_writer.write_attribute("height", &map[0].len());
        xml_writer.start_element("data");

        for row in map.clone() {
            for val in row {
                xml_writer.start_element("tile");
                xml_writer.write_attribute("gid", &val);
                xml_writer.end_element();
            }
        }
        xml_writer.end_element();
        xml_writer.end_element();

        xml_writer.end_element();

        let res = xml_writer.end_document();
        std::fs::write(path, res).unwrap();
    }
}
