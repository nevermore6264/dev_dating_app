import axios from 'axios';

export async function saveLocationToDB(locationData) {
  return await axios.post('/api/locations/save', locationData);
}