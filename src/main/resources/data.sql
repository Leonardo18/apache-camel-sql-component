CREATE TABLE [Todo](
    [Id] [int] NOT NULL IDENTITY PRIMARY KEY,
	[Name] [varchar](150) NOT NULL,
	[Description] [varchar](150) NOT NULL,
	[NU_CPF] [varchar](20) NULL,
	[priority] [int] NOT NULL,
	[author] [varchar](150) NULL
);