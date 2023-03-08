# Design

## Who is the target for this design, e.g., are you assuming any knowledge on the part of the language users?

The target was users who find it difficult to parse the original picobot language. My language is more english-adjacent such that it reads as a comprehensible sentence, almost.

## Why did you choose this design, i.e., why did you think it would be a good idea for users to express the maze-searching computation using this syntax?

When I was a CS5 student, I found the notation of expressing the conditionals
(N*Wx, for example) to be annoying to parse. It was hard for me to get used to it and decipher it with a quick glance. I wanted to build a language that would specifically make that more headache free, while also just improving the readability over all (at the expense of conciseness).

## What behaviors are easier to express in your design than in Picobot’s original design?  If there are no such behaviors, why not?

It is easier to write the conditional for the bot's surroundings. For instance, instead of writing N*Wx, we could write [N: open, W: open, S: blocked]. This makes it a lot easier to understand the state of the bot's surroundings. Additionally, we do not have to write unnecessary details because if a direction is left out, it is assumed to be "anything". In this case, East is not mentioned so it is automatically assigned to "can be any value". Furthermore, order does not matter; you could write [W: open, S: blocked, N: open] and get the same result which is convenient. 

## What behaviors are more difficult to express in your design than in Picobot’s original design? If there are no such behaviors, why not?

I don't think there are any behaviors that are more difficult, because picobot in general is pretty simple. My DSL is not as concise, which may be a minus but that's not very much of a behavioral thing.

## On a scale of 1–10 (where 10 is “very different”), how different is your syntax from PicoBot’s original design?

3, maybe? It's not incredibly different as it still uses the original picobot structure as a skeleton. 

## Is there anything you would improve about your design?
Maybe try to brainstorm ways to make it a bit more concise so the lines aren't as long. 