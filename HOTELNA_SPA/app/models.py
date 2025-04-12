class Spa:
    def __init__(self, name, description='', price=0.0, available=True, _id=None):
        self.id = str(_id) if _id else None
        self.name = name
        self.description = description
        self.price = price
        self.available = available

    def to_dict(self):
        return {
            'id': self.id,
            'name': self.name,
            'description': self.description,
            'price': self.price,
            'available': self.available
        }

    @staticmethod
    def from_mongo(document):
        return Spa(
            name=document.get('name'),
            description=document.get('description'),
            price=document.get('price'),
            available=document.get('available'),
            _id=document.get('_id')
        )
