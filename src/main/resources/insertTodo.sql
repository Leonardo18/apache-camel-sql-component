INSERT INTO
    Todo(
            Name,
            Description,
            Priority,
            Author
        )
        OUTPUT Inserted.ID
        VALUES
        (
            :#${in.body.name},
            :#${in.body.description},
            :#${in.body.priority},
            :#${in.body.author}
        )