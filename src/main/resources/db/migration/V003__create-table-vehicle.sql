create table veiculo(
	id bigint not null auto_increment,
    estacionamento_id bigint not null,
    marca varchar(20) not null,
    modelo varchar(20) not null,
    cor varchar(20) not null,
    placa varchar(20) not null,
    tipo varchar(20) not null,
  
  primary key (id)
);

alter table veiculo add constraint fk_veiculo_estacionamento
foreign key (estacionamento_id) references estacionamento (id);