"""
Scoreboard Server Unit Test implemented with pytest and Flask test_client() functionality.
Author: Tom Sanford

To run, execute this in a terminal window:

    pytest-3 --cov --cov-branch --cov-report html

The test will require the same Amazon DynamoDB private key file configuration
that the Scoreboard Server itself requires for the boto3 module's connection establishing.

"""


from scoreboard_server import *
import pytest

class TestScoreboard:

    def test_retrieve_with_no_arg(self):
        with app.test_client() as client:
            res = client.get("/retrieve/")
            assert res.status_code == HTTPStatus.OK

    def test_retrieve_with_arg(self):
        with app.test_client() as client:
            res = client.get("/retrieve/3")
            assert res.status_code == HTTPStatus.OK

    def test_retrieve_with_bad_arg(self):
        with app.test_client() as client:
            res = client.get("/retrieve/error")
            assert res.status_code == HTTPStatus.BAD_REQUEST

    def test_add_via_get(self):
        with app.test_client() as client:
            res = client.get("/add/unit-test/-9000/")
            assert res.status_code == HTTPStatus.OK

    def test_add_via_get_to_post_endpoint(self):
        with app.test_client() as client:
            res = client.get("/add")
            assert res.status_code == HTTPStatus.OK

    def test_add_via_get_with_bad_args(self):
        with app.test_client() as client:
            res = client.get("/add/unit-test/error/")
            assert res.status_code == HTTPStatus.BAD_REQUEST

    def test_add_via_get_with_bad_args2(self):
        with app.test_client() as client:
            res = client.get("/add/error/")
            assert res.status_code == HTTPStatus.NOT_FOUND

    def test_add_via_post(self):
        with app.test_client() as client:
            json_args = {}
            json_args["username"] = "unit-test"

            info_dict = {}
            info_dict["highscore"] = -9000
            info_dict["extra_data"] = "pytest3"

            json_args["info"] = info_dict
            
            res = client.post("/add", json=json_args)
            assert res.status_code == HTTPStatus.OK

    def test_add_via_post_no_body(self):
        with app.test_client() as client:
            res = client.post("/add")
            assert res.status_code == HTTPStatus.BAD_REQUEST

    def test_add_via_post_non_dict_json(self):
        with app.test_client() as client:
            json_arg = "error"
            res = client.post("/add",json=json_arg)
            assert res.status_code == HTTPStatus.BAD_REQUEST

    def test_add_via_post_missing_required_json_keys(self):
        with app.test_client() as client:
            json_args = {}
            json_args["username"] = "unit-test"
            res = client.post("/add", json=json_args)
            assert res.status_code == HTTPStatus.BAD_REQUEST

    def test_add_via_post_missing_required_json_keys2(self):
        with app.test_client() as client:
            json_args = {}
            json_args["username"] = "unit-test"

            info_dict = {}
            info_dict["extra_data"] = "pytest3"

            json_args["info"] = info_dict

            res = client.post("/add", json=json_args)
            assert res.status_code == HTTPStatus.BAD_REQUEST

    def test_add_via_post_invalid_json_syntax(self):
        with app.test_client() as client:
            res = client.post("/add", data="{'error", content_type='application/json')
            assert res.status_code == HTTPStatus.BAD_REQUEST

    def test_userscore_scoretime_gen(self):
        u = UserScore("unit-test", -9000)
        assert u.scoretime != None

