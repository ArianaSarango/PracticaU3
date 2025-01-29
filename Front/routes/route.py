from flask import Blueprint, abort, request, render_template, redirect
import json
import requests
from flask import flash
from flask import Blueprint, jsonify


router = Blueprint ('router', __name__)

@router.route('/')
def home():

    return render_template('fragmento/inicial/login.html')

@router.route('/admin')
def admin():
    return render_template('fragmento/inicial/admin.html')

@router.route('/admin/hotel/register')
def view_register_hotel():
    r_hotel = requests.get("http://localhost:8086/api/hotel/list")
    data_hotel = r_hotel.json()
    r_generador = requests.get("http://localhost:8086/api/generador/list")
    data_generador = r_generador.json()

    return render_template('fragmento/hotel/registro.html', lista_hotel=data_hotel["data"],lista_generador=data_generador["data"])

@router.route('/admin/hotel/list')
def list_person(msg=''):
    r_hotel = requests.get("http://localhost:8086/api/hotel/list")
    data_hotel = r_hotel.json()
    r_generador = requests.get("http://localhost:8086/api/generador/list")
    data_generador = r_generador.json()
    print(data_hotel)
    
    return render_template('fragmento/hotel/lista.html', lista_hotel=data_hotel["data"],lista_generador=data_generador["data"])

@router.route('/admin/hotel/edit/<id>', methods=['GET'])
def view_edit_person(id):

    r = requests.get("http://localhost:8086/api/hotel/listType")
    lista_tipos = r.json()  # Guardamos la respuesta JSON


    r1 = requests.get(f"http://localhost:8086/api/hotel/get/{id}")     # Obtenemos los datos de hotel por ID

    if r1.status_code == 200:
        data_hotel = r1.json()

        if "data" in data_hotel and data_hotel["data"]:      # Verificamos que la respuesta contenga datos válidos
            hotel = data_hotel["data"]


            if hotel["tieneGenerador"]:     # Obtenemos los datos del generador asociado a la hotel
                r_generador = requests.get(f"http://localhost:8086/api/generador/get/{hotel['id']}")
                if r_generador.status_code == 200:
                    data_generador = r_generador.json()
                    generador = data_generador["data"] if "data" in data_generador else None
                else:
                    generador = None  # Si no se encontraron datos del generador, se inicializa a None

            else:
                generador = None # Si la hotel no tiene generador, se inicializa a None

            return render_template('fragmento/hotel/editar.html', lista=lista_tipos["data"], hotel=hotel, generador=generador)
        else:
            flash("No se encontraron datos para el hotel.", category='error')
            return redirect("/admin/hotel/list")
    else:
        flash("Error al obtener el hotel", category='error')
        return redirect("/admin/hotel/list")
    


@router.route('/admin/hotel/save', methods=['POST'])
def save_person():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    data_hotel = { 
        "canton": form["can"],
        "apellidoPaternoaterno": form["ape"],
        "apellidoMaternoaterno": form["apem"],
        "integrantes": form["inte"],
        "tieneGenerador": form["tieneg"] == 'true' 
    }

    if form["tieneg"] == 'true':  # Solo si tiene generador
        data_generador = {
            "costo": form["cost"],
            "consumoXHora": form["conxh"],
            "energiaGenerada": form["energen"],
            "uso": form["uso"],
        }
    else:
        data_generador = { # Inicializa el generador a valores predeterminados
            "costo": 0,  
            "consumoXHora": 0,  
            "energiaGenerada": 0, 
            "uso": 'ninguno', 
        }

    r_hotel = requests.post("http://localhost:8086/api/hotel/save", data=json.dumps(data_hotel), headers=headers)     # Hacer la petición para guardar la hotel
    
    requests.post("http://localhost:8086/api/generador/save", data=json.dumps(data_generador), headers=headers)    # Hacer la petición para guardar el generador


    if r_hotel.status_code == 200:

        flash("Registro guardado correctamente", category='info')
        return redirect('/admin/hotel/list')
    else:
        flash(r_hotel.json().get("data", "Error al guardar la hotel"), category='error')
        return redirect('/admin/hotel/list')

        
@router.route('/admin/hotel/update', methods=['POST'])
def update_person():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    data_hotel = {
        "id": form["id"],
        "canton": form["can"],
        "apellidoPaternoaterno": form["ape"],
        "apellidoMaternoaterno": form["apem"],
        "integrantes": form["inte"],
        "tieneGenerador": form["tieneg"] == 'true'
    }

    if form["tieneg"] == 'true':  # Solo si tiene generador
        data_generador = {
            "id": form["id"],  # Usamos el mismo ID que la hotel
            "costo": form["cost"],
            "consumoXHora": form["conxh"],
            "energiaGenerada": form["energen"],
            "uso": form["uso"],
        }
    else:

        data_generador = {  # Inicializa el generador a valores predeterminados
            "id": form["id"],  
            "costo": 0,  
            "consumoXHora": 0,  
            "energiaGenerada": 0,  
            "uso": 'ninguno',  
        }

    r_generador = requests.post("http://localhost:8086/api/generador/update", data=json.dumps(data_generador), headers=headers)

    if r_generador.status_code != 200:
        flash("Error al actualizar el generador: " + r_generador.json().get("data", ""), category='error')
   
    r_hotel = requests.post("http://localhost:8086/api/hotel/update", data=json.dumps(data_hotel), headers=headers)
    
    if r_hotel.status_code == 200:
        flash("Registro de hotel guardado correctamente", category='info')
        return redirect('/admin/hotel/list')
    else:
        flash(r_hotel.json().get("data", "Error al actualizar la hotel"), category='error')
        return redirect('/admin/hotel/list')

@router.route('/admin/hotel/delete/<int:id>', methods=['POST'])
def delete_hotel(id):
   
    requests.delete(f"http://localhost:8086/api/hotel/delete/{id}")  # Solicitar la eliminación de la hotel y el generador asociado
 
    requests.delete(f"http://localhost:8086/api/generador/delete/{id}")

       
    return redirect('/admin/hotel/list')    # Redirigimos al usuario a la lista de hotels


def load_data(file_path):
    with open(file_path, 'r') as file:
        return json.load(file)

@router.route('/admin/hotel_generador', methods=['GET'])
def get_hotel_generador_data():
    # Cargamos datos de hotels y generadores desde JSON
    hotels = load_data('/home/aris/Documents/Taller_Domingo_LINKED_LIST/Taller_Domingo_2json/src/main/java/Data/hotel.json')
    generadores = load_data('/home/aris/Documents/Taller_Domingo_LINKED_LIST/Taller_Domingo_2json/src/main/java/Data/Generador.json')


    hotels_generadores = []      # Creamos lista para almacenar la relación de hotels y generadores
    for hotel in hotels:
        generador = next((g for g in generadores if g['id'] == hotel['id']), None)
        hotels_generadores.append({
            "hotel": hotel,
            "generador": generador
        })

    response = requests.get('http://localhost:8086/api/hotel/contadorGeneradores')     # Obtenemos  el conteo de hotels con generador
    data = response.json()
    hotels_con_generador = data['hotelsConGenerador']

    total_hotels = len(hotels)     # Calcular el total de hotels encuestadas

    return render_template( # Juntamos los datos 
        'fragmento/hotel/hotel_generador.html',
        hotels_generadores=hotels_generadores,
        hotels_con_generador=hotels_con_generador,
        total_hotels=total_hotels
    )

@router.route('/admin/hotel/buscar/<criterio>/<texto>')
def view_buscar_hotel(criterio, texto):
    url = 'http://localhost:8086/api/hotel/list/buscar/'
    if criterio == 'apellidoPaterno':
        r = requests.get(url+"apellidoPaterno/"+texto)
    elif criterio == 'apellidoMaterno':
        r = requests.get(url+"apellidoMaterno/" + texto)
    elif criterio == 'canton':
        r = requests.get(url+"canton/" + texto)
    elif criterio == 'integrantes':
        r = requests.get(url+"integrantes/" + texto)
    elif criterio == 'integrantesBinario':
        r = requests.get(url+"integrantesBinario/" + texto)
    elif criterio == 'id':
        r = requests.get(url+"id/" + texto)

    data1 = r.json()
    print(f"Response JSON: {data1}")
    if(r.status_code == 200):
        if type(data1["data"]) is dict:
            list=[]
            list.append(data1["data"])
            return render_template ('fragmento/hotel/lista.html', lista_hotel = list)
            print(f"Lista procesada (dict): {lista}")

        else:
            print(f"Lista procesada (lista): {data1['data']}")
            return render_template ('fragmento/hotel/lista.html', lista_hotel = data1["data"])

    else:
        return render_template ('fragmento/hotel/lista.html', lista_hotel = [], message = 'No existe el elemento')
    

@router.route('/admin/hotel/order/<atributo>/<tipo>/<metodo>')
def view_order_hotel(atributo, tipo, metodo):
    url = 'http://localhost:8086/api/hotel/list/order/'+atributo+"/"+tipo+"/"+metodo

    r = requests.get(url)

    data1 = r.json()
    if(r.status_code == 200):    
        return render_template ('fragmento/hotel/lista.html', lista_hotel = data1["data"])

    else:
        return render_template ('fragmento/hotel/lista.html', lista_hotel = [], message = 'No existe el elemento')
    



