<jsp:include page="intranetValida.jsp" />
<!DOCTYPE html>
<html lang="esS" >
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Expires" content="-1" />
<meta http-equiv="Cache-Control" content="private" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrapValidator.js"></script>
<script type="text/javascript" src="js/global.js"></script>

<link rel="stylesheet" href="css/bootstrap.css"/>
<link rel="stylesheet" href="css/dataTables.bootstrap.min.css"/>
<link rel="stylesheet" href="css/bootstrapValidator.css"/>

<title>Intranet</title>
</head>
<body>
<jsp:include page="intranetCabecera.jsp" />
<div class="container" style="margin-top: 4%"><h4>Asignación Pasatiempo</h4></div>

<form id="id_form" >
	<div class="container">
		<div class="row" style="margin-top: 1%">
			<div class="col-md-6">
				<label class="control-label" for="id_usuario">Usuario</label> 
				<select id="id_usuario" name="idUsuario" class='form-control'>
					<option value="-1"> [Seleccione] </option>
				</select>
			</div>
		</div>	
		<div class="row" style="margin-top: 1%">
			<div class="col-md-6">
				<label class="control-label" for="id_pasatiempo">Pasatiempo</label> 
				<select id="id_pasatiempo" name="idPasatiempo" class='form-control'>
					<option value="-1"> [Seleccione] </option>
				</select>
			</div>
		</div>
		<div class="row" style="margin-top: 3%">
			<div class="col-md-12" align="center">
				<button type="button" class="btn btn-primary" id="id_btn_agregar">Agregar</button>
			</div>
		</div>
		<div class="row" style="margin-top: 3%">
			<div class="col-md-12">
				<table id="id_table" class="table table-striped table-bordered">
					<thead>
						<tr>
							<th style="width: 10%">Código</th>
							<th style="width: 75%">Nombre</th>
							<th style="width: 15%"></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
		</div>
</form>
<script type="text/javascript">
$.getJSON("listaUsuario", {}, function(data){
	$.each(data, function(i,item){
		$("#id_usuario").append("<option value="+item.idUsuario +">"+ item.nombreCompleto +"</option>");
	});
});

$.getJSON("listaPasatiempo", {}, function(data){
	$.each(data, function(index,item){
		$("#id_pasatiempo").append("<option value="+item.idPasatiempo +">"+ item.nombre +"</option>");
	});
});

$("#id_usuario").change(function(){
	var var_usuario = $("#id_usuario").val();
	$.getJSON("listaPasatiempoPorUsuario", {"idUsuario":var_usuario }, function(data){
		agregarGrilla(data , var_usuario);
	});
});

$("#id_btn_agregar").click(function(){
	$.ajax({
        type: "POST",
        url: "registraPasatiempo", 
        data: $('#id_form').serialize(), //------------> ENVÌA como variable "data" toda la informaciòn recogida en el formulario del .jsp
        success: function(data){
      	  agregarGrilla(data.lista, data.usuario);
      	  mostrarMensaje(data.mensaje);
        },
        error: function(){
      	  mostrarMensaje(MSG_ERROR);
        }
      });
});


function agregarGrilla(lista, var_usuario){
	 $('#id_table').DataTable().clear();
	 $('#id_table').DataTable().destroy();
	 $('#id_table').DataTable({
			data: lista,
			searching: false,
			ordering: true,
			processing: true,
			pageLength: 10,
			lengthChange: false,
			columns:[
				{data: "idPasatiempo"},
				{data: "nombre"},
				{data: function(row, type, val, meta){
				    var salida='<button type="button" style="width: 90px" class="btn btn-warning btn-sm" onclick="accionEliminar(\'' + var_usuario +'\',\'' + row.idPasatiempo +'\')">Eliminar</button>';
					return salida;
				},className:'text-center'},	
			]                                     
	    });
}

function accionEliminar(idUsuario, idPasatiempo){
	$.ajax({
        type: "POST",
        url: "eliminaPasatiempo", 
        data: {"idUsuario": idUsuario, "idPasatiempo": idPasatiempo}, //ENVÌA idUsuario e idPasatiempo para que el controller los coja como paràmetros
        success: function(data){
      	  agregarGrilla(data.lista, data.usuario);
      	  mostrarMensaje(data.mensaje);
        },
        error: function(){
      	  mostrarMensaje(MSG_ERROR);
        }
     });
}
</script>   		
</body>
</html>