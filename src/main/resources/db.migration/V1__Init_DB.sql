create table car (
    id integer not null auto_increment,
    carmodel varchar(255) not null,
    carclass integer not null,
    totalcostcar integer not null,
    year varchar(255) not null,
    fuelconsumption float,
    enginevolume float,
    transmission varchar(255) not null,
    cost float not null,
    carstatus integer not null,
    imagecar varchar(255) not null,
    primary key (id)) engine=InnoDB;

create table carclass (
    id integer not null auto_increment,
    carclass varchar(255) not null,
    primary key (id)) engine=InnoDB;

create table carstatus (
    id integer not null auto_increment,
    carstatus varchar(255) not null,
    primary key (id)) engine=InnoDB;

create table degreeofdamage (
    id integer not null auto_increment,
    title varchar(255) not null,
    damagepercentage integer not null,
    primary key (id)) engine=InnoDB;

create table discount (
    id integer not null auto_increment,
    amountofdays integer not null,
    percent integer not null,
    primary key (id)) engine=InnoDB;

create table login (
    id integer not null auto_increment,
    username varchar(255) not null,
    password varchar(255) not null,
    role varchar(255) not null,
    user_id integer not null,
    primary key (id)) engine=InnoDB;

create table request (
    id integer not null auto_increment,
    user integer not null,
    car_id integer not null,
    startofrental date not null,
    endofrental date not null,
    reduction float not null,
    price float not null,
    repairbill integer,
    status varchar(255) not null,
    creationtime datetime(6) not null,
    primary key (id)) engine=InnoDB;

create table user (
    id integer not null auto_increment,
    name varchar(255) not null,
    surname varchar(255) not null,
    dateofbirth date not null,
    email varchar(255) not null,
    passportdata varchar(255),
    status varchar(255) not null,
    primary key (id)) engine=InnoDB;

alter table car
    add constraint carclass
    foreign key (carclass) references carclass (id);

alter table car
    add constraint carstatus
    foreign key (carstatus) references carstatus (id);

alter table login
    add constraint user_id
    foreign key (user_id) references user (id);

alter table request
    add constraint car_id
    foreign key (car_id) references car (id);

alter table request
    add constraint user
    foreign key (user) references user (id)
