extern crate rand;

use std::io;
use rand::Rng;

fn main() {

    let secret_number = rand::thread_rng()
        .gen_range(1, 101);

    println!("Guess the number");

    println!("The secret number is: {}", secret_number);
    println!("Please input your guess.");
    let mut guess = String::new();

    io::stdin().read_line(&mut guess)
        .expect("Failed to read line");
    
    // this is a comment same as in C
    println!("You guessed {}", guess);

    /* 
     * multi line comment
     * zzzz
     */

    
}
