from flask import Flask, jsonify, request
from flask_restx import Api, Resource, fields
from werkzeug.exceptions import BadRequest, NotFound

# 1. สร้าง Flask app และ Flask-RESTx API
app = Flask(__name__)
# กำหนดค่าสำหรับ API docs (Swagger UI) ให้ใช้งานง่ายขึ้น
api = Api(app,
          version='1.0',
          title='Movie Portal API',
          description='A simple API for managing movie playlists',
          doc='/', # จะทำให้ Swagger UI ปรากฏที่ http://127.0.0.1:5000/
          prefix='/api' # เพิ่ม prefix /api ให้กับทุก endpoints
         )

# Namespace สำหรับ Movie Portal
movie_ns = api.namespace('movies', description='Movie playlist operations')

# 2. สร้างโครงสร้างข้อมูลสำหรับ "MoviePortal"
#    กำหนด Model สำหรับภาพยนตร์แต่ละเรื่อง
movie_item_model = movie_ns.model('MovieItem', {
    'movie_title': fields.String(required=True, description='The title of the movie')
})

# กำหนด Model สำหรับ Playlist ทั้งหมด
playlist_model = movie_ns.model('Playlist', {
    'playlist_id': fields.String(required=True, description='Unique identifier of the playlist', min_length=1),
    'playlist_name': fields.String(required=True, description='Name of the playlist', min_length=1),
    'movie_list': fields.List(fields.Nested(movie_item_model), description='List of movies in the playlist', required=True)
})

# 3. กำหนดข้อมูลเริ่มต้นสำหรับ "MoviePortal" (3 เพลย์ลิสต์)
#    ใช้เป็น list ของ dictionary เพื่อให้ง่ายต่อการค้นหาและจัดการ
playlists_data = [
    {
        "playlist_id": "P001",
        "playlist_name": "datenight",
        "movie_list": [
            {"movie_title": "The Notebook"},
            {"movie_title": "50 First Dates"},
            {"movie_title": "A Walk to Remember"}
        ]
    },
    {
        "playlist_id": "P002",
        "playlist_name": "action",
        "movie_list": [
            {"movie_title": "Die Hard"},
            {"movie_title": "Mad Max: Fury Road"},
            {"movie_title": "John Wick"}
        ]
    },
    {
        "playlist_id": "P003",
        "playlist_name": "comedy",
        "movie_list": [
            {"movie_title": "Superbad"},
            {"movie_title": "Step Brothers"},
            {"movie_title": "The Hangover"}
        ]
    }
]

# 4. สร้างเมธอด API สำหรับจัดการเพลย์ลิสต์
#    Resource สำหรับจัดการเพลย์ลิสต์ทั้งหมด
@movie_ns.route('/playlists')
class PlaylistList(Resource):
    @movie_ns.doc('list_all_playlists')
    @movie_ns.marshal_list_with(playlist_model) # ใช้ marshal_list_with เพื่อบอกว่า return เป็น list ของ playlist_model
    def get(self):
        """
        Get all movie playlists
        """
        return playlists_data, 200 # Return data and 200 OK status

    @movie_ns.doc('create_new_playlist')
    @movie_ns.expect(playlist_model, validate=True) # ใช้ expect เพื่อรับข้อมูลตาม playlist_model และทำการ validation
    @movie_ns.marshal_with(playlist_model, code=201) # marshal_with สำหรับ return ค่าเมื่อสร้างสำเร็จ
    def post(self):
        """
        Create a new movie playlist
        """
        new_playlist = api.payload

        # ตรวจสอบว่า playlist_id ซ้ำหรือไม่
        for p in playlists_data:
            if p['playlist_id'] == new_playlist['playlist_id']:
                movie_ns.abort(409, f"Playlist with ID '{new_playlist['playlist_id']}' already exists")

        playlists_data.append(new_playlist)
        return new_playlist, 201 # Return the newly created playlist and 201 Created status

# Resource สำหรับจัดการเพลย์ลิสต์ทีละรายการด้วย playlist_id
@movie_ns.route('/playlists/<string:playlist_id>')
@movie_ns.param('playlist_id', 'The unique identifier of the playlist')
class Playlist(Resource):
    @movie_ns.doc('get_playlist_by_id')
    @movie_ns.marshal_with(playlist_model)
    def get(self, playlist_id):
        """
        Get a specific movie playlist by its ID
        """
        for p in playlists_data:
            if p['playlist_id'] == playlist_id:
                return p, 200
        movie_ns.abort(404, f"Playlist with ID '{playlist_id}' not found")

    @movie_ns.doc('update_playlist')
    @movie_ns.expect(playlist_model, validate=True)
    @movie_ns.marshal_with(playlist_model)
    def put(self, playlist_id):
        """
        Update an existing movie playlist
        """
        updated_data = api.payload
        for i, p in enumerate(playlists_data):
            if p['playlist_id'] == playlist_id:
                # ถ้า ID ใน body ไม่ตรงกับ ID ใน URL parameter จะเกิดความสับสน
                if updated_data['playlist_id'] != playlist_id:
                    movie_ns.abort(400, "Playlist ID in body must match ID in URL")
                playlists_data[i] = updated_data
                return updated_data, 200
        movie_ns.abort(404, f"Playlist with ID '{playlist_id}' not found")

    @movie_ns.doc('delete_playlist')
    def delete(self, playlist_id):
        """
        Delete a movie playlist
        """
        global playlists_data # เพื่อให้สามารถแก้ไข playlists_data ได้
        initial_len = len(playlists_data)
        playlists_data = [p for p in playlists_data if p['playlist_id'] != playlist_id]
        if len(playlists_data) < initial_len:
            return {'message': f"Playlist with ID '{playlist_id}' deleted successfully"}, 204 # 204 No Content
        movie_ns.abort(404, f"Playlist with ID '{playlist_id}' not found")


# หากต้องการรันแอปพลิเคชัน
if __name__ == '__main__':
    # รันใน debug mode เพื่อให้ Flask-RESTx ทำการรีโหลดเมื่อมีการเปลี่ยนแปลงโค้ด
    # host='0.0.0.0' เพื่อให้สามารถเข้าถึงจาก IP อื่นๆ ในเครือข่ายได้
    app.run(debug=True, host='127.0.0.1', port=5000)
